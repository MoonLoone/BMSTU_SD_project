import React, { useState, useEffect } from 'react';
import BackendService from "../../services/BackendService";
import PaginationComponent from '../PaginationComponent';

const UsersListComponent = props => {

    const [users, setUsers] = useState([]);
    const [hidden, setHidden] = useState(false);
    const [page, setPage] = useState(0);
    const [totalCount, setTotalCount] = useState(0);
    const limit = 10;

    const onPageChanged = cp => {
        refreshUsers(cp - 1)
    }

    const refreshUsers = cp => {
        BackendService.retrieveAllUsers(cp, limit)
            .then(
                resp => {
                    setUsers(resp.data.content);
                    console.log(users)
                    setHidden(false);
                    setTotalCount(resp.data.totalElements);
                    setPage(cp);
                }
            )
            .catch(() => {
                setHidden(true);
                setTotalCount(0);
            })
    }

    if (hidden)
        return null;
    return (
        <div className="m-4">
            <div className="row my-2">
                <h3>Пользователи</h3>
            </div>
            <div className="row my-2 me-0">
                <PaginationComponent
                    totalRecords={totalCount}
                    pageLimit={limit}
                    pageNeighbours={1}
                    currentPage={page}
                    onPageChanged={onPageChanged} />
                <table className="table table-sm">
                    <thead className="thead-light">
                        <tr>
                            <th>Id</th>
                            <th>Имя пользователя</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            users && users.map((user) =>
                                <tr key={user.id}>
                                    <td width="10%">{user.id}</td>
                                    <td>{user.login}</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )


}


export default UsersListComponent;
