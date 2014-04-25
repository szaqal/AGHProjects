%Uruchamia uczenie 
%1. ekstrakcja cech
%2. sklejone wektory cech
%3. uruchomienie adaboost
function trees = runTrain()


fprintf('Zbior uczacy\n');
files = trainSet;

fprintf('**** Zbior uczacy (Ekstrakcja Cech)****\n');
faces = fileLoader(files);
    currentClass=0;
    z=0;
    x=0;
    fprintf('\nZbior trenujacy o rozmiarze %d\n',size(faces,2));
    for i=1:size(faces,2)
        features=haarFeature(faces{i});
        %fprintf('%f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f\n', features);
        z=i;
        if z==6  %liczone od 1 WRRRRR!!!
            currentClass=currentClass+1;
            z=0;
        end
        
        featuresSet(i,:)=horzcat(features,currentClass);
        %fprintf('%f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f (%d)\n', features, currentClass);
    end
    %end

%Kazdy z kazdym!!
featuresSetSize = size(featuresSet,1);
i=1;
for x=1:featuresSetSize
    for y=1:featuresSetSize
        trainigSet(i,:)=horzcat(featuresSet(x,:),featuresSet(y,:));
        i=i+1;
    end
    %fprintf('---- %d ----',i);
end

trainingSetSize=size(trainigSet,1);
class1Index=32; %indeks kolumny w macierzy
class2Index=64; %indeks kolumny w macierzy
for i=1:trainingSetSize
    class1 = trainigSet(i,class1Index);
    class2 = trainigSet(i,class2Index);
    if class1==class2
        classesTrain(i,1)=1; %1-gdy ta sama osoba
    else
        classesTrain(i,1)=0; %0-gdy rozne osoby
    end
end

trainigSet(:,class1Index)=[]; %usuniecie smieci
trainigSet(:,class2Index-1)=[]; %usuniecie smieci


%Uczenie klasyfikatora
fprintf('**** UCZENIE ****');
trees = adaboost(trainigSet, classesTrain, 20);

%---- TEST NA ZBIOZE UCZACYM ----
% classes = adaboostResults(trees, trainigSet);
% correct=0;
% incorrect=0;
% for i=1:trainingSetSize
%     foundClass=classes(i,:);
%     expectedClass=classesTrain(i,:);
%     if foundClass==expectedClass
%         correct=correct+1;
%     else
%         incorrect=incorrect+1;
%     end
%      fprintf('-> Class %d <-> %d', foundClass, expectedClass);
%      fprintf('\n');
% end
% fprintf('-> Poprawne %d, niepoprawne %d\n', correct, incorrect);
% fprintf('-> Poprawne %.1f %', (correct/trainingSetSize)*100);