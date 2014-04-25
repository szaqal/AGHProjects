%non pederastians zbior testowy
function result = test_np()
imgLocation = 'C:\Agh_Mro_Features\test\non-pedestrians\';
files=[];
for j=1:75
    fileName=[imgLocation, num2str(randint(1,1,[1,1000])),'.pgm'];
    %fprintf('%s\n', fileName);
    files{j}=fileName;
end
result = files;
