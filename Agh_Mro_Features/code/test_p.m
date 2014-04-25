%pederastians zbior testowy
function result = test_p()
imgLocation = 'C:\Agh_Mro_Features\test\pedestrians\';
files=[];
for j=1:75
    fileName=[imgLocation, num2str(randint(1,1,[1,960])),'.pgm'];
    %fprintf('%s\n', fileName);
    files{j}=fileName;
end
result = files;
