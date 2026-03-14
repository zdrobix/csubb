function y=reducereper(x,digits=1144)
 doipi=vpa(2*sym(pi),digits);
 x=vpa(x,digits);
 y=double(mod(x,doipi));
 