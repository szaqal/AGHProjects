% Usuwa ceche okreslona indeksem ze wszystkich zbiorow
% testowych/treningowych
function [ nTrainP, nTrainNp, nTestP, nTestNp ] = removeFeature( trainPed, trainNp, testP, testNp, featureToRemove )

nTrainP=trainPed;
nTrainNp=trainNp;
nTestP=testP;
nTestNp=testNp;

nTrainP(:,featureToRemove)=[];
nTrainNp(:,featureToRemove)=[];
nTestP(:,featureToRemove)=[];
nTestNp(:,featureToRemove)=[];

