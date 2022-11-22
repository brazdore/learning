import './styles.css';

import CarHeader from '../../assets/images/car-header.png';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <>
      <div className={'home-container'}>
        <div className={'container'}>
        <div className={'base-card home-card'}>
          <div className={'home-image-container'}>
            <img src={CarHeader} alt='car-header' />
          </div>
          <div className={'home-content-container'}>
            <div>
              <h1>O carro perfeito para você</h1>
              <p>Conheça nossos carros e dê mais um passo na realização do seu sonho</p>
            </div>
            <div>
            </div>
          </div>
        </div>
        <div className={'base-card bottom-card-div'}>
          <div className={'bottom-btn-div'}>
            <Link to={'/catalog'}>
            <button className={'btn btn-secondary bottom-btn'}>
              <h6>VER CATÁLOGO</h6>
            </button>
            </Link>
          </div>
          <div className={'bottom-content-div'}>
            <h6>Comece agora a navegar</h6>
          </div>
        </div>
      </div>
      </div>
    </>
  );
};

export default Home;