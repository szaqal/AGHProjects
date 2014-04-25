% Najzwyklejszy przbieg
% 1-uczenie
% 2-testwanie
addpath('C:\Agh_Mro_Features\code\osu-svm');
addpath('C:\Agh_Mro_Features\code\adaBoost');
addpath('C:\Agh_Mro_Features\code\common');
warning off all;
tic; 

%Ekstrakcja cech NONPEDERASTIANS
fprintf('Zbior uczacy (NON-PEDERASTIANS)\n');
nPedFeat = NonPederastianFeatures(train_np);

%Ekstrakcja cech PEDERASTIANS
fprintf('Zbior uczacy (PEDERASTIANS)\n');
pedFeat = PederastianFeatures(train_p);


fprintf('Uczenie\n');
[train classesTrain]=prepareTrain(pedFeat, nPedFeat);
trees = adaboost(train, classesTrain, 20);



train_no_prederastians = train_np;
train_pederastians = train_p;   
fprintf('Zbior testujacy (PEDERASTIANS)\n');
pFeat = PederastianFeatures(test_p);
fprintf('Zbior testujacy (NON-PEDERASTIANS)\n');
npFeat = NonPederastianFeatures(test_np);

fprintf('Testowanie');
[overAll, newRocData]=runTest(trees, pFeat, npFeat);
roc(newRocData);


toc;