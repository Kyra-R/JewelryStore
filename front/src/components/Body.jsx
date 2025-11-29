import '../styles/App.css';
import '../styles/body.css';
import logo from '../logo.svg';
import dm from '../images/diamond-ring.png';
import ear from '../images/earrings.png';
import jew from '../images/jewelry.png';
import neck from '../images/necklace.png';
import reb from '../images/rebuild.png';
import wed from '../images/wedding-ring.png';
import Chat from "./Chat";

function Body(props) {
    return (
        <div className='body'>
		
	   <Chat />
  
  <div class="container">
    
   
    <div class="main-grid">
      <div class="gallery block-1">
        <img src={dm} />
        <p>Кольца - наиболее популярные ювелирные изделия. Закажите себе одно прямо сейчас!</p>
      </div>
      <div class="gallery block-3">
        <img src={ear} />
        <p>Серьги подчеркнут красоту любой женщины.</p>
      </div>
      <div class="gallery block-4">
        <img src={jew} />
        <p>Лучше всего заказать набор украшений в одном стиле. </p>
      </div>
      <div class="gallery block-5">
        <img src={neck} />
        <p>Подвески и ожерелья - это одни из древнейших украшений.</p>
      </div>
      <div class="gallery block-6">
        <img src={reb} />
        <p>Что-то не так? Обратитесь в нашу службу поддержки: 8-800-555-35-35</p>
      </div>
      <div class="gallery block-7">
        <img src={wed} />
        <p> Вы можете купить у нас свадебные кольца для Вас и Вашего партнера.</p>
      </div>
    </div>


    


    <footer>
         

    </footer>

  </div>


			
        </div>
    );
}


export default Body;
