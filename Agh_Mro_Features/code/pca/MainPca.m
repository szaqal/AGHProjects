function void = MainPca()
addpath('C:\Agh_Mro_Features\code\osu-svm');
addpath('C:\Agh_Mro_Features\code\adaBoost');
addpath('C:\Agh_Mro_Features\code\common');
addpath('C:\Agh_Mro_Features\code');
warning off all;
tic;
fprintf('**** UCZENIE (PCA)****\n');

%Ekstrakcja cech NONPEDERASTIANS
fprintf('Zbior uczacy (NON-PEDERASTIANS)\n');
nPedFeat = NonPederastianFeatures(train_np);

%Ekstrakcja cech PEDERASTIANS
fprintf('Zbior uczacy (PEDERASTIANS)\n');
pedFeat = PederastianFeatures(train_p);

featuresCount = size(nPedFeat,2)-1;


fprintf('Zbior testujacy (PEDERASTIANS)\n');
testPFeat = PederastianFeatures(test_p);

fprintf('Zbior testujacy (NON-PEDERASTIANS)\n');
testNpFeat = NonPederastianFeatures(test_np);


fprintf('PCA\n');
trainNPedPca=pca(nPedFeat, featuresCount);
trainPedPca=pca(pedFeat, featuresCount);

testNPedPca=pca(testNpFeat, featuresCount);
testNPedPca(:, featuresCount+1)=0;
testPedPca=pca(testPFeat, featuresCount);
testPedPca(:, featuresCount+1)=1;

overAll =0;
overAllRoc = [];
for i=2:featuresCount
    trainPedSet = trainPedPca(:,1:i);
    trainPedSet(:,i+1)=1;
    trainNPedSet = trainNPedPca(:,1:i);
    trainNPedSet(:,i+1)=0;
    
    fprintf('Trenowanie\n');
    [train classesTrain]=prepareTrain(trainPedSet, trainNPedSet);
    trees = adaboost(train, classesTrain, 20);
    
    testPedSet = testPedPca(:,1:i);
    testPedSet(:,i+1)=1;
    testNPedSet = testNPedPca(:,1:i);
    testNPedSet(:,i+1)=1;
   
    fprintf('Testowanie\n');
    [result rocData] = runTest(trees, testPedSet, testNPedSet);
    if result > overAll
        overAll = result;
        overAllRoc = rocData;
    end
    
end
fprintf('Best overall %f \n', overAll);
overAllRoc
roc(overAllRoc);
toc;