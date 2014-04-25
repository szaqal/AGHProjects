%Zawiera liste plikow do wczytania pederastians -> zbior treningowy
function result  = train_p()
fprintf('Ladowanie zbioru trenujacy/pederastians\n');
imgLocation = 'C:\Agh_Mro_Features\training\pedestrians\';
files=[];
for j=1:100
    fileName=[imgLocation, num2str(randint(1,1,[1,1440])),'.pgm'];
    %fprintf('%s\n', fileName);
    files{j}=fileName;
end
result = files;
