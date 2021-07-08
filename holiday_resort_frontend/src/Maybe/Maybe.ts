/* HAS TO BE ANY BECAUSE OF ITS GENERIC NATURE */ 
/* ...todo */

const Nothing = () => ({     
  map : (_ : any) => Nothing(),
  flatMap : (_ : any) => Nothing(),
  emit : () => Nothing(),
  inspect : () => 'Nothing',
});

const Just = (x : any) => ({
  map : (fn : any) : any => Maybe(fn(x)),
  flatMap : (fn : any)  => fn(x),
  emit : () => x,
  inspect : () => 'Just($x)',
});


const Maybe = (x : any)  => (x === null || x === undefined ) ? Nothing() : Just(x);

export default Maybe;
