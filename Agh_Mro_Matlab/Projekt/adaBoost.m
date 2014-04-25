%
% Klasyfikator ADA-Boost budowany na bazie drzew decyzyjnych
% Wzory http://en.wikipedia.org/wiki/AdaBoost
%
function decisionTreeList = adaBoost(trainSet, classesSet, steps)
    trainSize = size(trainSet,1);
    D = ones(trainSize,1)/trainSize;
    T = steps;

    step = 1;
    index = 1;
    alpha = 1;
    error = 1;
    trees = {}; %wynikowy zbior drzew
       
    while((step <= T) && (error > 0))
        t = treefit(trainSet, classesSet, 'method','classification');   %tworzenie drzewa
        trees{index} = t;
        trees{index+1} = alpha;
        
        yfit = treeval(t, trainSet); %wektor przewidywanych wynikow
        
        %blad
        tmp = abs(yfit-1-classesSet);
        tmp=tmp(1:size(D,1));
        error = tmp' * D;

		alpha = 0.5 * log((1-error)/error); %wzor wikipedia
		
		[trainSet, classesSet] = createNewSet(trainSet, classesSet, D);
		
		%nowe wagi
		D = updateD(yfit-1, classesSet, alpha, D); %Obliczenie Dt+1
			
		index = index+2;
		step = step + 1; 
    end
   
    
    decisionTreeList = trees;      
