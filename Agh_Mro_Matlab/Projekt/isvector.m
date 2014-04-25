function isv = isvector ( u ) ;

 [ m, n ] = size(u);
 isv = ( max(m,n) ~= 1 & min(m,n) == 1 );
