%Typy borazkow uzyte dla kazdej postaci w zbioze trenujacym
function result = trainSet()
facesTypes = {'centerlight','glasses','happy','leftlight','noglasses'};
result = fileNames(facesTypes);
end
