import './App.css';

import { BrowserRouter as Router ,Routes, Route, Navigate } from 'react-router-dom';

import Bar from './components/Bar';
import SignInForm from './components/Aurhentication/SignInForm';
import SignUpForm from './components/Aurhentication/SignUpForm';
import Home from './components/Home/Home';
import Profile from './components/Profile/Profile';

const App = () => {
	return (
		<div style={{background: 'linear-gradient(94deg, rgba(47, 93, 170, 0.97) 0%, rgba(116, 34, 102, 0.97) 100%)', minHeight:'100vh', overflow:'hidden'}}>
			<Router>
				<Bar />
				<Routes>
					<Route path='/' element={<Home/>}/>
					<Route path='/signin' element={<SignInForm />}/>
					<Route path='/signup' element={<SignUpForm />}/>
					<Route path='/profile' element={<Profile/>}/>
					<Route path="*" element={<Navigate to="/" />} />
				</Routes>
			</Router>
		</div>
	);
}

export default App;
