function [newTrainSet, newClassesSet] = createNewSet(trainSet, classesSet, D)


s = sum(sum(D));
if s ~= 0
    D = D./s;
else
    D = D;
end

%fprintf('-->%f,', size(D,1));
[vals, idx] = sortrows(D,1); 
vals=flipud(vals);
idx=flipud(idx);
    
trainSetTmp = trainSet(idx,:);
classesSetTmp = classesSet(idx, :);
    
tSize = size(trainSet, 1);
vals = ceil(vals.*tSize);      
    
i=1;
valIndex=1;
    
newTrainSet = ones(tSize, size(trainSet, 2));
newClassesSet = ones(tSize, 1);
while (i <= tSize)
    for j = i:i+vals(valIndex)-1          
        newTrainSet(j, :) = trainSetTmp(valIndex,:);
    end
    newClassesSet(i:i+vals(valIndex)-1) = classesSetTmp(valIndex);
    i = i+vals(valIndex);
    valIndex=valIndex+1;
end
