function void = main()

wavs = {'haar','db2', 'coif3', 'rbio3.5','bior3.5'}; % rodzaje falek

%fid = fopen('wynik.txt','wt');
%tic
for p = 1:2 % 1 - sqr, 2 - abs
    for w = 1:size(wavs, 2) % rozne falki
        if p == 1
           fprintf('%s %s\n',wavs{w}, 'sqr');
%           fprintf(fid,'%s %s\n',wavs{w}, 'sqr');
        elseif p == 2
            fprintf('%s %s\n',wavs{w}, 'abs');
%            fprintf(fid,'%s %s\n',wavs{w}, 'abs');
        end
%        tic
        for level = 1:15 %rozne poziomy dekpmpozycji    
            train = 1; %indeks w macierzy tr.
            test = 1; %indeks w macierzy tes
            fprintf('%d\t', level);
%            fprintf(fid,'%d\t', level);
            %wczytuje obrazki do macierzy trenujacej i testujacej
            for i = 1:112
                if i ~= 14
                image = imread(['D:\studia\9semestr\mro\moje\lab3\datasets\brodatz\D',num2str(i),'-LU.png']);
                trainMatrix(train, :) = createFeat(level, image, wavs{w}, p);
                image = imread(['D:\studia\9semestr\mro\moje\lab3\datasets\brodatz\D',num2str(i),'-RU.png']);
                trainMatrix(train+1, :) = createFeat(level, image, wavs{w}, p);
                image = imread(['D:\studia\9semestr\mro\moje\lab3\datasets\brodatz\D',num2str(i),'-LD.png']);
                testMatrix(test, :) = createFeat(level, image, wavs{w}, p);
                image = imread(['D:\studia\9semestr\mro\moje\lab3\datasets\brodatz\D',num2str(i),'-RD.png']);
                testMatrix(test+1, :) = createFeat(level, image, wavs{w}, p);
                train =  train + 2;
                test = test+2;
                end
            end
            error = findNN(trainMatrix, testMatrix);
            fprintf('%f\n', error);
%            fprintf(fid,'%f\n', error);

            clear trainMatrix;
            clear testMatrix;
        end
%        toc
    end 
end
%toc
%fclose(fid);
%void = error;
end