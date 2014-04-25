function [correctPercent, correct, testingSetSize, rocData] = TestNonPederastians( trees, npFeat ) 
%fprintf('**** TESTOWANIE (Non pederastians)****\n');
featureSet = npFeat(:,1:(size(npFeat,2)-1));
classes = adaboostResults(trees, featureSet);

testingSetSize = size(featureSet,1);
correct = 0;
rocData = [];
for i=1:testingSetSize
    foundClass=classes(i,:);        % zwrocone przez klasyfikator
    expectedClass=npFeat(i,size(npFeat,2)); % obliczone (prawidlowe)
    
    rocData(i,1)=i;
    rocData(i,2)=foundClass;
    
    if foundClass==expectedClass
        correct=correct+1;
    end
end


correctPercent = (correct/testingSetSize)*100;
