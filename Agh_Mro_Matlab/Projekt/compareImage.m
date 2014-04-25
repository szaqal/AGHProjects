%
% Porowananie dwoch obrazow w oparciu 
% o drzewa decyzyjne ze zbudowanego klasyfikatora
%
function void = compareImage( pic1, pic2, trees )
filesToLoad={pic1, pic2};
loadedImages=fileLoader(filesToLoad);
featuresFace1=haarFeature(loadedImages{1}); % cechy z pierwszego obrazu wejsciowego
featuresFace2=haarFeature(loadedImages{2}); % cechy z drugiego obrazu wejsciowego
testSet(1,:)=horzcat(featuresFace1(1,:),featuresFace2(1,:)); %polaczenie wektorow
classes = adaboostResults(trees, testSet); %proba klasyfikacji wektora cech

if classes(1,:)==0
    fprintf('-> Rozne osoby');
else
    fprintf('-> Te same osoby');
end

end
