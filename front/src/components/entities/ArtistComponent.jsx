import React, {useState} from "react";
import BackendService from "../../services/BackendService";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import{faChevronLeft, faSave} from "@fortawesome/free-solid-svg-icons";
import {alertActions} from "../../utils/Rdx";
import{connect} from "react-redux";
import {Form} from "react-bootstrap";
import { useParams, useNavigate } from 'react-router-dom';

const ArtistComponent = props => {

    const [hidden, setHidden] = useState(false);
    const { id } = useParams();
    const navigate = useNavigate();
    const [artist, getArtist] = useState({name:"", id:0});

    const changeName = ({name}) => {
        artist.name = name
    };

    const onSubmit = (event) => {
        event.preventDefault();
        event.stopPropagation();
        let err = null;
        if (!artist.name){
            err = "Имя должно быть указано"
        }
        let countr = {id: artist.id, name: artist.name}
        if (countr.id ===-1) {
            BackendService.createArtist(countr)
                .catch(()=>{})
        }
        else {
            BackendService.updateArtist(countr)
                .catch(()=>{})
        }
    }

    const navigateToArtists = () => {
        navigate('/artists')
    }

    /*const componentDidMount = () => {
        if (parseInt(this.state.id)!==-1){
            BackendService.retrieveAllArtists(this.state.id)
                .then((resp)=>{
                    setState({
                        name: resp.data.name,
                    })
                })
                .catch(()=>setState({hidden: true}))
        }
    }*/

        if (hidden)
            return null;
        return (
            <div className="m-4">
                <div className="row my-2 mr-0">
                    <h3>Художник</h3>
                    <button
                        className="btn btn-outline-secondary ml-auto"
                        onClick={()=>  navigateToArtists }><FontAwesomeIcon
                        icon={faChevronLeft}/>{' '}Назад</button>
                </div>
                <Form onSubmit={onSubmit}>
                    <Form.Group>
                        <Form.Label>Имя</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Введите имя художника"
                            onChange={changeName}
                            value={artist}
                            name="name"
                            autoComplete="off"/>
                    </Form.Group>
                    <button
                        className="btn btn-outline-secondary"
                        type="submit"><FontAwesomeIcon
                        icon={faSave}/>{' '}Сохранить</button>
                </Form>
            </div>
        )

}

export default ArtistComponent;