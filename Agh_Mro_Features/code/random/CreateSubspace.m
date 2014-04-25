function outMatrix = CreateSubspace( inMatrix, features )
limit=size(features,2);
for i=1:limit
    featureColumn = features(:,i);
    outMatrix(:,i)=inMatrix(:,featureColumn);
end
outMatrix(:,limit+1)=inMatrix(:,size(inMatrix,2)); %klasy trzeba przepisac
