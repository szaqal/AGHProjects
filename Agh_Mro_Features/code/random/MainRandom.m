%
%       LOSOWA SELEKCJA
%
addpath('C:\Agh_Mro_Features\code\osu-svm');
addpath('C:\Agh_Mro_Features\code\adaBoost');
addpath('C:\Agh_Mro_Features\code\common');
addpath('C:\Agh_Mro_Features\code');
warning off all;
fprintf('**** UCZENIE (LOSOWA PODPRZESTREN)****\n');

tic;
%Ekstrakcja cech NONPEDERASTIANS
fprintf('Zbior uczacy (NON-PEDERASTIANS)\n');
nPedFeat = NonPederastianFeatures(train_np);

%Ekstrakcja cech PEDERASTIANS
fprintf('Zbior uczacy (PEDERASTIANS)\n');
pedFeat = PederastianFeatures(train_p);


fprintf('Zbior trenujacy (PEDERASTIANS)\n');
pFeat = PederastianFeatures(test_p);

fprintf('Zbior trenujacy (NON-PEDERASTIANS)\n');
npFeat = NonPederastianFeatures(test_np);

bestOverAllCorrect =0;
bestChoosedFeatures = [];

tryNumber=5;
bestRoc = [];
for i=1:tryNumber;
    numberOfFeaturesToChoose = randint(1,1,[1,82]);
    choosedFeatures = randint(1,numberOfFeaturesToChoose,[1,82]); %Wektor indeksow kolumn z ktorych podprzestrzen bedzie stworzona
    subNPedFeat = CreateSubspace(nPedFeat, choosedFeatures);
    subPedFeat = CreateSubspace(pedFeat, choosedFeatures);
    
    %uczenie na podprzestrzeni
    fprintf('Uczenie\n');
    [train classesTrain]=prepareTrain(subPedFeat, subNPedFeat);
    trees = adaboost(train, classesTrain, 20);
    
    subPFeat = CreateSubspace(pFeat, choosedFeatures);
    subNpFeat = CreateSubspace(npFeat, choosedFeatures);
    
    fprintf('Testowanie\n');
    [overAllCorrect rocData]= runTest(trees, subPFeat, subNpFeat);
    
    if overAllCorrect > bestOverAllCorrect
         bestOverAllCorrect = overAllCorrect;
         bestChoosedFeatures = choosedFeatures;
         bestRoc=rocData;
    end
     
end

%podumowanie
%bestOverAllCorrect
%bestChoosedFeatures
roc(bestRoc);
toc;

