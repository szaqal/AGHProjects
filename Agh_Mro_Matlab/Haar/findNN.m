function result = findNN(trainMatrix, testMatrix)
    correct = 0; %poprawnie znalezione
    testRow = size(testMatrix, 1);
    trainRow = size(trainMatrix, 1);

    for testIter = 1:testRow
        [minVal minIndex] = min(sum((trainMatrix(:,:) - repmat(testMatrix(testIter,:), trainRow, 1)).^2,2));%min wartosc i jej indeks
        if mod(minIndex,2) == 0
            minIndex2 = minIndex - 1;
        else
            minIndex2 = minIndex + 1;
        end
        if  testIter == minIndex || testIter == minIndex2 %spr czy dobrze dopasowal
            correct = correct + 1;
        end
    end

    find = correct;
    precision = correct/testRow * 100;
    error = 100 - precision;

    result = error;
end