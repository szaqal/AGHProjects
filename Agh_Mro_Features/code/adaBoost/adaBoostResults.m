%
%Wyrodukuje wektor klas dla zadanych przykladow
%
function results = adaBoostResults( trees, data )
results = zeros(size(data,1), 1);

i = 1;
while (i < size(trees,2))   
   tclass = treeval(trees{i}, data);
   tclass = tclass - 1;
   results = results + tclass * trees{i+1};
   i = i + 2;
end

for j=1:size(data,1)
    if(results(j) > 0)
        results(j) = 1;
    else
        results(j) = 0;
    end
end
