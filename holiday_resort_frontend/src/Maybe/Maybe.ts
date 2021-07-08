/* HAS TO BE ANY BECAUSE OF ITS GENERIC NATURE */ 
/* ...todo */

const Nothing = () => ({     
  map : (_ : any) => Nothing(),
  flatMap : (_ : any) => Nothing(),
  emit : () => Nothing(),
  inspect : () => 'Nothing',
  isNothing : true,
  isSomething : false,
});

const Just = (x : any) => ({
  map : (fn : any) : any => Maybe(fn(x)),
  flatMap : (fn : any)  => fn(x),
  emit : () => x,
  inspect : () => 'Just($x)',
  isNothing : false,
  isSomething : true,
});

const Maybe = (x : any)  => (x === null || x === undefined || x.isNothing) ? Nothing() : Just(x);

export default Maybe;
