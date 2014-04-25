%
%       SEKWENCYJNA ELIMINACJA
%

addpath('C:\Agh_Mro_Features\code\osu-svm');
addpath('C:\Agh_Mro_Features\code\adaBoost');
addpath('C:\Agh_Mro_Features\code\common');


fprintf('**** UCZENIE (SEKWENCYJNA ELIMINACJA)****\n');
[train classesTrain]=runTrain;
featuresCount = size(train,2);
for i=1:featuresCount
    newTrain = train(:,i:featuresCount) %kazda cecha
end