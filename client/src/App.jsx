import './App.css';

import { BrowserRouter as Router ,Routes, Route, Navigate } from 'react-router-dom';

import { useSelector } from 'react-redux';

import Bar from './components/Bar';
import SignInForm from './components/Aurhentication/SignInForm';
import SignUpForm from './components/Aurhentication/SignUpForm';
import Home from './components/Home/Home';
import Profile from './components/Profile/Profile';
import Admin from './components/Admin/Admin';
import ShowAllCars from './components/ShowCars/ShowAllCars';

const App = () => {
	const user = useSelector((state) => state.user);

	const renderRoutes = () => {
		let routes = [
			<Route key="profile" path="/profile" element={<Profile />} />
		];
	
		if (user) {
			if (user.roles.includes('ROLE_ADMIN')) {
				routes.push(<Route key="admin" path="/admin" element={<Admin />} />);
			}
		} else {
			routes = [
				<Route key="signIn" path="/signin" element={<SignInForm />} />,
				<Route key="signOut" path="/signup" element={<SignUpForm />} />
			];
		}
		
		return routes
	}
	
	return (
		<div style={{background: 'linear-gradient(94deg, rgba(47, 93, 170, 0.97) 0%, rgba(116, 34, 102, 0.97) 100%)', minHeight:'100vh', overflow:'hidden'}}>
			<Router>
				<Bar />
				<Routes>
					<Route path='/' element={<Home/>}/>
					<Route path='/cars' element={<ShowAllCars/>}/>
					{renderRoutes()}
					<Route path="*" element={<Navigate to="/" />} />
				</Routes>
			</Router>
		</div>
	);
}

export default App;
