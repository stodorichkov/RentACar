import './App.css';
import Bar from './components/Bar';
import { ThemeProvider } from '@mui/material/styles';
import theme from './theme';
import { Avatar } from '@mui/material';

const App = () => {
	return (
		<>
			<ThemeProvider theme={theme}>
				<Bar />
			</ThemeProvider>
		</>
	);
}

export default App;
