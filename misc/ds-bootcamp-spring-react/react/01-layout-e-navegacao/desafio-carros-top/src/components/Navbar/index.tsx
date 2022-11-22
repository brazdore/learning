import './styles.css';
import 'bootstrap/js/src/collapse';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav className='navbar navbar-expand-md navbar-light bg-primary main-nav'>
      <div className={'container'}>
        <Link to={'/'}>
          <h4>Carros Top</h4>
        </Link>
        <button
          className='navbar-toggler'
          type='button'
          data-bs-toggle='collapse'
          data-bs-target='#carros-top-navbar'
          aria-controls='carros-top-navbar'
          aria-expanded='false'
          aria-label='Toggle navigation'
        >
          <span className='navbar-toggler-icon'></span>
        </button>

        <div className={'navbar-collapse collapse content-div'} id={'carros-top-navbar'}>
          <ul className={'navbar-nav offset-md-2 main-menu'}>
            <li>
              <Link to={'/'}>
                Home
              </Link>
            </li>
            <li>
              <Link to={'/catalog'}>
                Catálogo
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
