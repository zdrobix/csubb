function R=pade_sym(f,m,k)
x=symvar(f);
if k==0
   R=taylor(f,x,'order',m+1);
else
   c = sym(zeros(1,k)); r = c;  d = c;
   for i=0:k-1
       c(i+1)=taylor_coef(f,m+i);
       r(i+1)=taylor_coef(f,m-i);
       d(i+1)=-taylor_coef(f,m+i+1);
   end
   C=toeplitz(c,r);
   if det(C)==0
     b=zeros(size(d'));
   else
     b=C\d';%rezolva sistemul C*b=d' , d'=transpusa lui d
   endif
   b=[1;b];
   a=sym(zeros(m+1,1));
   for j=0:m
       for l=0:min([j k])
           a(j+1)=a(j+1)+taylor_coef(f,j-l)*b(l+1);
       end
   end
   R=(x.^(0:m)*a)/(x.^(0:k)*b);
 end


