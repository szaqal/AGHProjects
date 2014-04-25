%
%       SEKWENCYJNA SELEKCJA
%

addpath('C:\Agh_Mro_Features\code\osu-svm');
addpath('C:\Agh_Mro_Features\code\adaBoost');
addpath('C:\Agh_Mro_Features\code\common');
addpath('C:\Agh_Mro_Features\code');
warning off all;

tic;

fprintf('**** UCZENIE (SEKWENCYJNA SELEKCJA)****\n');
trainPFeat = PederastianFeatures(train_p);
trainNpFeat = NonPederastianFeatures(train_np);

[train classesTrain]=prepareTrain(trainPFeat, trainNpFeat);
featuresCount = size(train,2);

%pobranie wektorow cech dla zbioru testujacego
testPFeat = PederastianFeatures(test_p);
testNpFeat = NonPederastianFeatures(test_np);


bestFeature=-2;
newFeatureIndex = 1;
currentBest = 0; %ogolnie najlepsza cecha
rocBest = [];
while bestFeature~=-1
    bestFeature = -1;
    featuresCount = size(trainPFeat,2)-1; %bez klasy
    
    
    localBest = 0; %lokalnie najlepsza cecha
    for i=1:featuresCount
        [train classesTrain]=prepareTrain(trainPFeat, trainNpFeat);
        trees = adaboost(train, classesTrain, 20);  
        
        overAllCorrect = runTest(trees, testPFeat, testNpFeat);
        if overAllCorrect > localBest
            localBest = overAllCorrect;
            bestFeature = i;
        end
    end

    
    %nowe cechy
    
    if(bestFeature ~=-1)
        newTrainPFeat(:,newFeatureIndex)=trainPFeat(:,bestFeature);
        newTrainNpFeat(:,newFeatureIndex)=trainNpFeat(:,bestFeature);
        newTestPFeat(:,newFeatureIndex)=testPFeat(:,bestFeature);
        newTestNpFeat(:,newFeatureIndex)=testNpFeat(:,bestFeature);
    
         newFeatureIndex=newFeatureIndex+1;
        %klasy na koncu
        newTrainPFeat(:,newFeatureIndex)=1;
        newTrainNpFeat(:,newFeatureIndex)=0;
        newTestPFeat(:,newFeatureIndex)=1;
        newTestNpFeat(:,newFeatureIndex)=0;
    
        %usuniecie zapamietanych najlepszych
        trainPFeat(:,bestFeature)=[];
        trainNpFeat(:,bestFeature)=[];
        testPFeat(:,bestFeature)=[];
        testNpFeat(:,bestFeature)=[];

        fprintf('Uczenie');
         [train classesTrain]=prepareTrain(newTrainPFeat, newTrainNpFeat);
        trees = adaboost(train, classesTrain, 20);     
        fprintf('Testowanie');
        [overAllCorrect rocData]= runTest(trees, newTestPFeat, newTestNpFeat);
        if(overAllCorrect > currentBest) 
            currentBest = overAllCorrect;
            rocBest = rocData;
        end
        clear trees;
        fprintf('Correct %f \n', overAllCorrect);
    end

    
end
fprintf('Final Best %f \n', currentBest);
roc(rocBest);
toc;
