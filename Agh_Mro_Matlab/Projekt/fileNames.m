function result = fileNames(facesTypes)
facesCount = {'01','02','03'};
facesLocation = 'C:\Agh_Mro_Matlab\Ds\';

filesN=[];
%Wczytanie obrazow
z=1;
    for j = 1:size(facesCount,2)
           for i = 1:size(facesTypes,2)
               fileName=[facesLocation, 'subject',facesCount{j}, '.',facesTypes{i}];
               %fprintf('%s\n', fileName);
               %image(faceImg);
               filesN{z}=fileName;
               z=z+1;
           end
    end
result = filesN;
