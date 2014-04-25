function results = haarFeature2(arg)
%A=[4 1 2 2; 0 4 1 3; 3 1 0 4; 2 1 3 2] %test
%arg=A;
doubleImg = im2double(arg);

intImg=integral(doubleImg);
featureSize=2;
j=0;

[rows cols]=size(intImg);
countRows = rows/featureSize;
countCols = cols/featureSize;

z=1;
for j=1:countCols
    for i=1:countRows
        pt1=intImg(i,j);
        pt2=intImg(i,j+featureSize);
        pt3=intImg(i+featureSize, j);
        pt4=intImg(i+featureSize,j+featureSize);
        %fprintf('[p1 %d, pt2 %d, pt3 %d, pt4 %d]\n', pt1, pt2, pt3, pt4);
        sumVal=pt4-pt3-pt2+pt1;
        tempResults(1,z)=sumVal;
        z=z+1;
    end
    i=1;
end

tempResults;

j=1;
z=1;
while j<size(tempResults,2)
    sumVal = tempResults(1,j)+tempResults(1,j+1);
    results(1,z)=sumVal;
    j=j+2;
    z=z+1;
end
results;


