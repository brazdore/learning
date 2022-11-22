import './styles.css';
import ProductImg from 'assets/images/car-card.png';

const ProductCard = () => {
  return (
    <>
      <div className={'base-card product-card'}>
        <div className={'card-img-container'}>
          <img src={ProductImg} alt={'Carro vermelho irado'} />
        </div>
        <div className={'card-name-container'}>
          <h5>Audi Supra TT</h5>
        </div>
        <div className={'card-description-container'}>
          <h6>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate, nisi</h6>
        </div>
        <div className={'card-btn-container'}>
          <button className={'btn btn-secondary bottom-btn'}>
            <h6>COMPRAR</h6>
          </button>
        </div>
      </div>
    </>
  );
};

export default ProductCard;