%
% Dt+1 wikipedia
%
function newD = updateD(sfit, classes, alfa, oldD)
    newD=[];
    for i = 1:length(oldD);        
        if sfit(i) == classes(i)
            factor = exp(alfa);
        else
            factor = exp(-alfa);
        end
        newD(i) = oldD(i) * real(factor);
    end
    Z = sum(sum(newD));
    if Z ~= 0
        newD=newD./Z;
    end
    newD=newD';
