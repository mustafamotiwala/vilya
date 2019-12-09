import App from './App.svelte';
import page from 'page';
import "./app.scss";
import Login from './pages/login.svelte';

const renderHome = () => {
	new App({
		target: document.body,
		props: {
			name: 'world'
		}
	});
};

const renderLogin = ()=>{
	console.info("render login invoked...");
	new Login({
		target: document.body,
	});
};

page('/', renderHome);
page('/login', renderLogin);
page({hashbang: true});
// page({});
//export default app;
