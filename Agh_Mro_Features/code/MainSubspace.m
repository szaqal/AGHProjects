addpath('C:\Agh_Mro_Features\code\osu-svm');
addpath('C:\Agh_Mro_Features\code\adaBoost');
addpath('C:\Agh_Mro_Features\code\common');


fprintf('**** UCZENIE (Podprzestrzen)****\n');
[train classesTrain]=runTrain;
train
%trees = adaboost(train, classesTrain, 20);
