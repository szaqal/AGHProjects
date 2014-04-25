%Testowanie
%1. ekstrakcja cech testowanych obrazkow
%2. Wrzucenie do klasyfikatora i sprawdzenie czy 1 czy 0
function [overAll, newRocData] = runTest(trees, pFeat, npFeat)


[PcorrectPercent, Pcorrect, PtestingSetSize pRocData] = TestPederastians(trees, pFeat);
[NPcorrectPercent, NPcorrect, NPtestingSetSize nRocData] = TestNonPederastians(trees, npFeat);
%fprintf('-> Pederastians Poprawne %.1f \n', PcorrectPercent);
%fprintf('-> Non pederastians Poprawne %.1f \n', NPcorrectPercent);

overAll= (Pcorrect + NPcorrect)/(PtestingSetSize+NPtestingSetSize);
fprintf('-> Calosciowo Poprawne %.1f \n', overAll*100);

newRocData = vertcat(pRocData, nRocData);
b=1:150;
newRocData(:,1)=b';
%roc(newRocData);
%--------------------------------------------------------------------------

% 
% for GammaRBF = 1:5:40    
% 	for C_RBF = 1:50:1000
%         % SVM z kernelem RBF
%         [AlphaY, SVs, Bias, Parameters, nSV, nLabel] = RbfSVC(trainNormalized', classesTrain', GammaRBF,C_RBF);
% 
%         %testowanie SVM
%         [testValuesSVM, DecisionValue]= SVMClass(testNormalized', AlphaY, SVs, Bias, Parameters, nSV, nLabel);
% 
% 		specificity = sum(classesTest == 0 & testValuesSVM' == 0)/sum(testValuesSVM' == 0);
% 		sensitivity = sum(classesTest == 1 & testValuesSVM' == 1)/sum(testValuesSVM' == 1);
%         error = sum(abs(classesTest' - testValuesSVM))/size(classesTest,1);
%         
%         fprintf('%d\t %d\t %f\t %f\t %f\t\n', GammaRBF, C_RBF, specificity, sensitivity, error);
%     end
% end