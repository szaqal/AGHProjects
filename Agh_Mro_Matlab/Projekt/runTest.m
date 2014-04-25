%Testowanie
%1. ekstrakcja cech testowanych obrazkow
%2. sklejenie nowego wektora cech
%3. Wrzucenie do klasyfikatora i sprawdzenie czy 1 czy 0
function void = runTest(trees)
%------------------------------------   Testowanie    ------------------------------------
fprintf('**** \nZbior testujacy\n ****');
files = testSet;
faces = fileLoader(files);
  currentClass=0;
  fprintf('\nZbior testujacy o rozmiarze %d\n',size(faces,2));
  for i=1:size(faces,2)
     features=haarFeature(faces{i});
        %fprintf('%f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f %f\n', features)
        z=i;
        if z==7  %liczone od 1 WRRRRR!!!
            currentClass=currentClass+1;
            z=0;
        end
        
        featuresSet(i,:)=horzcat(features,currentClass);
  end



%Kazdy z kazdym!!
featuresSetSize = size(featuresSet,1);
i=1;
for x=1:featuresSetSize
    for y=1:featuresSetSize
        testingSet(i,:)=horzcat(featuresSet(x,:),featuresSet(y,:));
        i=i+1;
    end
end

testingSetSize=size(testingSet,1);
class1Index=32; %indeks kolumny w macierzy
class2Index=64; %indeks kolumny w macierzy
for i=1:testingSetSize
    class1 = testingSet(i,class1Index);
    class2 = testingSet(i,class2Index);
    if class1==class2
        classesTest(i,1)=1; %1-gdy ta sama osoba (wzgledem tego bedzie sprawdzenie)
    else
        classesTest(i,1)=0; %0-gdy rozne osoby (wzgledem tego bedzie sprawdzenie)
    end
end

testingSet(:,class1Index)=[]; %usuniecie smieci
testingSet(:,class2Index-1)=[]; %usuniecie smieci


fprintf('**** TESTOWANIE ****');
classes = adaboostResults(trees, testingSet);
correct=0;
incorrect=0;
sameCorrect=0;              %te same twarze poprawnie rozpoznane
sameIncorrect=0;            %te same twarze niepoprawnie rozpoznane
differentCorrect=0;         %rozne twarze poprawnie rozpoznane
differentIncorrect=0;       %rozne twarze niepoprawnie rozpoznane
sameFacesCount=0;           %calkowita ilosc kombinacji tych samych twarzy
differentFacesCount=0;      %calkowita ilosc kombinacji roznych tawrzy
rocData = [];
for i=1:testingSetSize
    foundClass=classes(i,:);        % zwrocone przez klasyfikator
    expectedClass=classesTest(i,:); % obliczone (prawidlowe)
    rocData(i,1)=i;
    rocData(i,2)=foundClass;
    
    %Poprawnie zklasyfikowane
    if foundClass==expectedClass
        correct=correct+1;
        
        
        %gdy te same twarze zwieksz calkowita ilosc roznych kombinacji
        %tych samych twarzy
        if expectedClass==1
            sameFacesCount=sameFacesCount+1;
            sameCorrect=sameCorrect+1;
        end   
        
        %gdy rozne twarza zwieksz calkowita ilosc roznych kombinacji
        %roznych twarzy
        if expectedClass==0
            differentFacesCount=differentFacesCount+1;
            differentCorrect=differentCorrect+1;
        end
        
    else
       %Nie poprawnie zklasyfikowane 
        incorrect=incorrect+1;
        
        %gdy te same twarze zwieksz calkowita ilosc roznych kombinacji
        %tych samych twarzy
        if expectedClass==1
            sameFacesCount=sameFacesCount+1;
            sameIncorrect=sameIncorrect+1;
        end   
        
        %gdy rozne twarza zwieksz calkowita ilosc roznych kombinacji
        %roznych twarzy
        if expectedClass==0
            differentFacesCount=differentFacesCount+1;
            differentIncorrect=differentIncorrect+1;
        end
        
        
        
    end
     %fprintf('-> Class %d <-> %d', foundClass, expectedClass);
     %fprintf('\n');
end


%roc(rocData); %odkomentowac jak potrzebne

fprintf('-> Poprawne %d, niepoprawne %d calosc %d\n', correct, incorrect, testingSetSize);
fprintf('-> Poprawne %.1f %', (correct/testingSetSize)*100);
fprintf('\n----------------------');
fprintf('\n-> Klasa TE SAME Poprawne %d, niepoprawne %d calosc %d', sameCorrect, sameIncorrect, sameFacesCount);
fprintf('\n-> Klasa Te same %.1f poprawnie', (sameCorrect/sameFacesCount)*100);
fprintf('\n-> Klasa Te same %.1f nie poprawnie', (sameIncorrect/sameFacesCount)*100);
fprintf('\n----------------------');
fprintf('\n-> Klasa ROZNE Poprawne %d, niepoprawne %d calosc %d', differentCorrect, differentIncorrect, differentFacesCount);
fprintf('\n-> Klasa Rozne %.1f poprawnie', (differentCorrect/differentFacesCount)*100);
fprintf('\n-> Klasa Rozne %.1f nie poprawnie', (differentIncorrect/differentFacesCount)*100);



