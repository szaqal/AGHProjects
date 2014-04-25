function result = fileLoader( arg )
%fprintf('Ladowanie zdjec');

loadedImages=[];

for i=1:size(arg,2)
    fprintf('%s\n', arg{i});
    loadedImages{i} = imread(arg{i});
end
%imshow(loadedImages{1});
result = loadedImages;


