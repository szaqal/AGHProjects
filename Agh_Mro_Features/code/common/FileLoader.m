function result = FileLoader( arg )
%fprintf('Ladowanie zdjec');

loadedImages=[];

for i=1:size(arg,2)
    %fprintf('%s\n', arg{i});
    loadedImages{i} = imread(arg{i});
end
%imshow(loadedImages{2});
result = loadedImages;
