import React from 'react';
import {BrowserRouter, Route} from 'react-router-dom'
import {CssBaseline, withStyles} from '@material-ui/core'
import SimpleTable from '../Bets';
import Calculate from "../Calculate";
import Home from "../Home";
import Register from "../Register";
import SignIn from "../SignIn";
import TopMenu from "../menu/TopMenu";
import LeftMenu from "../menu/LeftMenu";

class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: true,
            userId: null
        }
    }

    handleLoggedIn = (userId) => {
        this.setState({userId: userId});
    };

    handleLoggedOut = () => {
        this.setState({userId: null});
    };

    handleDrawerOpen = () => {
        this.setState({open: true});
    };

    handleDrawerClose = () => {
        this.setState({open: false});
    };

    render() {
        const {classes} = this.props;

        return (
            <div className={classes.root}>
                <CssBaseline/>

                <BrowserRouter>
                    <TopMenu open={this.state.open}
                             authenticated={this.state.userId}
                             onDrawerOpen={this.handleDrawerOpen}/>
                    <LeftMenu open={this.state.open}
                              onDrawerClose={this.handleDrawerClose}/>

                    <main className={classes.content}>
                        <div className={classes.appBarSpacer}/>
                        <Route exact path="/" component={Home}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/signin"
                               render={(props) => <SignIn onLoggedIn={this.handleLoggedIn} {...props} />}/>
                        <Route path="/calculate" component={Calculate}/>
                        <Route path="/bets" render={() =>
                            <SimpleTable/>
                        }/>
                    </main>

                </BrowserRouter>
            </div>
        );
    }
}

const drawerWidth = 240;

const styles = theme => ({
    root: {
        display: 'flex',
    },
    appBarSpacer: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
        height: '100vh',
        overflow: 'auto',
    },
});

export default withStyles(styles)(Dashboard);