%
%       SEKWENCYJNA ELIMINACJA
%
addpath('C:\Agh_Mro_Features\code\osu-svm');
addpath('C:\Agh_Mro_Features\code\adaBoost');
addpath('C:\Agh_Mro_Features\code\common');
addpath('C:\Agh_Mro_Features\code');
warning off all;

tic;
fprintf('**** UCZENIE (SEKWENCYJNA ELMINIACJA)****\n');
trainPed = PederastianFeatures(train_p);
trainNp = NonPederastianFeatures(train_np);



[train classesTrain]=prepareTrain(trainPed, trainNp);
trees = adaboost(train, classesTrain, 20);

testP = PederastianFeatures(test_p);
testNp = NonPederastianFeatures(test_np);

currentBest = runTest(trees, testP, testNp);
overAllBest = currentBest;
overAllRoc = [];
%--------------------Wlasciwa eliminacja ---------------------------
worstFeature=-2;
while worstFeature~=-1

worstFeature = -1;
featuresCount = size(trainPed,2)-1; %bez klasy


for featureRemove=1:featuresCount
    [ nTrainP, nTrainNp, nTestP, nTestNp ] = removeFeature( trainPed, trainNp, testP, testNp, featureRemove );

    [train classesTrain]=prepareTrain(nTrainP, nTrainNp);
    trees = adaboost(train, classesTrain, 20);

    [currentBest rocData]= runTest(trees, nTestP, nTestNp);
    if currentBest > overAllBest
        overAllBest = currentBest;
        overAllRoc = rocData;
        worstFeature = featureRemove;
    end
end

if worstFeature ~= -1
     fprintf('\nWorst Feature found\n');
     [ nTrainP, nTrainNp, nTestP, nTestNp ] = removeFeature( trainPed, trainNp, testP, testNp, worstFeature );
     trainPed=nTrainP;
     trainNp = nTrainNp;
     testP = nTestP;
     testNp = nTestNp;
 else
      fprintf('\nWorst Feature NOT found\n');
end


end %while

toc;

fprintf('Najlepszy wynik %f', overAllBest);
roc(rocData);