import './App.css';
import { ThemeProvider } from '@mui/material/styles';
import theme from './theme';
import CssBaseline from '@mui/material/CssBaseline';

import { BrowserRouter as Router ,Routes, Route, Navigate } from 'react-router-dom';

import Bar from './components/Bar';
import SignInForm from './components/SignInForm';
import SignUpForm from './components/SIgnUpForm';

const App = () => {
	return (
		<>
			<Router>
				<CssBaseline />
				<ThemeProvider theme={theme}>
					<Bar />
					<Routes>
						<Route path='/'/>
						<Route path='/signin' element={<SignInForm />}/>
						<Route path='/signup' element={<SignUpForm />}/>
						<Route path="*" element={<Navigate to="/" />} />
					</Routes>
				</ThemeProvider>  
			</Router>
		</>
	);
}

export default App;
