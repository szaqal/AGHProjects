function void = main()

%Trenowanie klasyfikatora
trees=runTrain;

%Testowanie klasyfikatora zbiorem testujacym

runTest(trees);


fprintf('\nRozmiar %d',size(trees));
 treedisp(trees{1});


%Tu mozna ustawic jakie 2 obrazki porownwac
%facesLocation = 'C:\Agh_Mro_Matlab\Ds\';
%fileName1=[facesLocation, 'subject01.glasses'];
%fileName2=[facesLocation, 'subject07.wink'];
%compareImage(fileName1, fileName2, trees);