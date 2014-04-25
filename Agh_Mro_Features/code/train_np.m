%Obrazy non pederastians zbior treningowy
function result  = train_np()
imgLocation = 'C:\Agh_Mro_Features\training\non-pedestrians\';
fprintf('Ladowanie zbioru - trenujacy/non-pederastias\n');
files=[];
for j=1:100
    fileName=[imgLocation, num2str(randint(1,1,[1,1500])),'.pgm'];
    %fprintf('%s\n', fileName);
    files{j}=fileName;
end
result = files;