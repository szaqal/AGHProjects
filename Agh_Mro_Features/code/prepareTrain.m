%Uruchamia uczenie 
%1. ekstrakcja cech
%Klasa 0 -> Non pederastians
%Klasa 1 -> Pederastians
function [train, classesTrain] = prepareTrain(pFeat, npFeat)

trainSet = cat(1, npFeat, pFeat); %skonkatenowane oba zbiory pionowo
train = trainSet(:,1:(size(trainSet,2)-1)); %tranujace wektory cech BEZ KLASY
classesTrain= trainSet(:,size(trainSet,2)); % zbior KLAS

%Uczenie klasyfikatora

%*** SVM ***
%[AlphaY, SVs, Bias, Parameters, nSV, nLabel] = RbfSVC(trainSet', classesTrain', 40,1000);
%[testValuesSVM, DecisionValue]= SVMClass(trainSet(12,:)', AlphaY, SVs,
%Bias, Parameters, nSV, nLabel);