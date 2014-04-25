function result = createFeat(level, image, wav, param )

doubleImg = im2double(image);
index = 3*level + 1; % ilosc macierzy po dekompozycji na n-tym poziomie
feat = zeros(1, index); % na cechy 

[c, s] = wavedec2(doubleImg, level, wav);
j=1;
low=1;
high = s(1,1)^2;
if param == 1
    feat(1, j) = sum(sum(c(:,1:high).^2));
elseif param == 2
    feat(1, j) = sum(sum(abs(c(:,1:high))));  
end
j= j+1;
for i = 2:level + 1
    for k = 1:3
        low = high+1;
        high = high + s(i, 1)^2;
        if param == 1
            feat(1, j) = sum(sum(c(:,low:high).^2));
        elseif param == 2
           feat(1, j) = sum(sum(abs(c(:,low:high)))); 
        end
        j = j+1;
    end
end
    result = (feat - sum(feat)/size(feat, 2))/std(feat); % normalizacja   
end