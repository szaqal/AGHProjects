function feat = PederastianFeatures(files)
%fprintf('**** \nZbior testujacy\n ****');
img = fileLoader(files);
  %fprintf('\nZbior o rozmiarze %d\n',size(img,2));
  for i=1:size(img,2)
     %features=haarFeature(img{i});
     features=haarFeature2(img{i});
     featuresSet(i,:)=horzcat(features,1);  % <-- 1-pederastians
  end

  feat=featuresSet;
