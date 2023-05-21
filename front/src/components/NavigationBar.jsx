import React from 'react';
import { Navbar, Nav } from 'react-bootstrap'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHome, faBars} from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Utils from '../utils/Utils';
import BackendService from '../services/BackendService';
import { connect } from "react-redux";
import { userActions } from "../utils/Rdx";

class NavigationBarClass extends React.Component {

    constructor(props) {
        super(props);
        this.goHome = this.goHome.bind(this);
        this.logout = this.logout.bind(this);
    }

    goHome() {
        this.props.navigate('Home');
    }

    render() {
        return (
            <Navbar bg="light" expand="lg">
                <button type="button" 
                    style={{marginLeft:'15px'}}
                    className="btn btn-outline-secondary mr-2"
                    onClick={this.props.toggleSideBar}>
                    <FontAwesomeIcon icon={faBars} />
                </button>
                <Navbar.Brand><FontAwesomeIcon icon={faHome} style={{marginLeft:'15px'}} />{' '}MoonMosem</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ms-auto">
                        <Nav.Link as={Link} to="/home">Home</Nav.Link>
                        <Nav.Link as={Link} to="/account">{this.props.user && this.props.user.login}</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
                {this.props.user &&
                    <Nav.Link onClick={this.logout}><FontAwesomeIcon fixedWidth />{' '}Выход</Nav.Link>
                }
                {!this.props.user &&
                    <Nav.Link as={Link} to="/login"><FontAwesomeIcon fixedWidth />{' '}Вход</Nav.Link>
                }
            </Navbar>
        );
    }

    logout() {
        BackendService.logout()
            .then(() => {
                Utils.removeUser();
                this.props.dispatch(userActions.logout())
                this.props.navigate('Login');
            })
    }

}

const NavigationBar = props => {
    const navigate = useNavigate()
    return <NavigationBarClass navigate={navigate} {...props} />
}

const mapStateToProps = state => {
    const { user } = state.authentication;
    return { user };
}

export default connect(mapStateToProps)(NavigationBar);

