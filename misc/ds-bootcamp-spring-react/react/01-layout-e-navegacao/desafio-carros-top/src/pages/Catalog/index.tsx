import ProductCard from '../../components/ProductCard';
import './styles.css';

const Catalog = () => {
  return (
    <>
      <div className={'main-div'}>
      <div className={'container'}>
        <div className={'base-card search-card-div'}>
          <form className='form-inline'>
            <input type={'text'} id={'searchForm'} placeholder={'Digite sua busca'} />
          </form>
          <button type='submit' form='searchForm' value='Submit' className={'btn btn-secondary'}>BUSCAR</button>
        </div>
      </div>
      <div className={'container my-3'}>
        <div className={'row'}>
          <div className={'col-sm-6 col-lg-4 col-xl-4'}>
            <ProductCard />
          </div>
          <div className={'col-sm-6 col-lg-4 col-xl-4'}>
            <ProductCard />
          </div>
          <div className={'col-sm-6 col-lg-4 col-xl-4'}>
            <ProductCard />
          </div>
          <div className={'col-sm-6 col-lg-4 col-xl-4'}>
            <ProductCard />
          </div>
          <div className={'col-sm-6 col-lg-4 col-xl-4'}>
            <ProductCard />
          </div>
        </div>
      </div>
      </div>
    </>
  );
};

export default Catalog;