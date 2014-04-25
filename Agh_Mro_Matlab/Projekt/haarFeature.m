%
%Obliczenie wektora cech przy uzyciu falek haara.
%Wynikiem jest znormalizowany wektor cech
%
function result = haarFeature(arg)
%image(arg);
level=10;
doubleImg = im2double(arg);
[c,s] = wavedec2(doubleImg, level,'haar');
j=1;
low=1;
high = s(1,1)^2;
feat(1, j) = sum(sum(abs(c(:,1:high))));  
j= j+1;
for i = 2:level + 1
    for k = 1:3
        low = high+1;
        high = high + s(i, 1)^2;
        feat(1, j) = sum(sum(abs(c(:,low:high)))); 
        j = j+1;
    end
end
% (feat - sum(feat)/size(feat, 2))/std(feat); % normalizacja  Z (surowe wektory bez normalizacji)
    result = feat;
