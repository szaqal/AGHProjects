function [correctPercent, correct, testingSetSize, rocData] = TestPederastians( trees, pFeat )
%------------------------------- PEDERASTIANS -----------------------------------  
%fprintf('**** TESTOWANIE (Pederastians)****\n');
featureSet = pFeat(:,1:(size(pFeat,2)-1));
classes = adaboostResults(trees, featureSet);

testingSetSize = size(featureSet,1);
correct = 0;
rocData = [];
for i=1:testingSetSize
    foundClass=classes(i,:);        % zwrocone przez klasyfikator
    expectedClass=pFeat(i,size(pFeat,2)); % obliczone (prawidlowe)
    
    rocData(i,1)=i;
    rocData(i,2)=foundClass;
    
    if foundClass==expectedClass
        correct=correct+1;
    end
end

correctPercent=(correct/testingSetSize)*100;

