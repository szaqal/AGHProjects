function intim = integralimage(im)
    
    intim = cumsum(cumsum(im,1),2);