function [Mres, eigenVectors, eigenValues] = pca(M,n)
    [pc, score, latent] = princomp(M);
    eigenVectors = pc;
    eigenValues = latent;
    Mres = score(:,1:n);
end