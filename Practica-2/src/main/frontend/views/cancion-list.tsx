import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, NumberField, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { CancionService, TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect, useState } from 'react';
import Cancion from 'Frontend/generated/com/unl/practica2/base/models/Cancion';


export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Cancion',
  },
};

const dateTimeFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
  timeStyle: 'medium',
});

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormUpdateProps = {
  onCancionUpdated?: () => void;
}

//Guardar Cancion 
function CancionEntryForm(props: CancionEntryFormProps) {

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal('');
  const genero = useSignal('');
  const album = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');

  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
        const id_genero = parseInt(genero.value) + 1;
        const id_album = parseInt(album.value) + 1;
        await CancionService.create(nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }

        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumGenero().then(data =>
      //console.log(data)
      listaGenero.value = data
    );
  }, []);

  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumCombo().then(data =>
      //console.log(data)
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      //console.log(data)
      listaTipo.value = data
    );
  }, []);

  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCancion}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del cancion"
            placeholder="Ingrese el nombre de la cancion"
            aria-label="Nombre del cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox label="Genero"
            items={listaGenero.value}
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
          />
          <ComboBox label="Album"
            items={listaAlbum.value}
            placeholder='Seleccione un album'
            aria-label='Seleccione un album de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
          />
          <ComboBox label="Tipo"
            items={listaTipo.value}
            placeholder='Seleccione un tipo de archivo'
            aria-label='Seleccione un tipo de archivo de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <NumberField label="Duracion"
            placeholder="Ingrese la Duracion de la cancion"
            aria-label="Nombre la Duracion de la cancion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="Link de la cancion"
            placeholder="Ingrese el link de la cancion"
            aria-label="Nombre el link de la cancion"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//ACTUALIZAR Cancion
function CancionEntryFormUpdate(props: CancionEntryFormUpdateProps) {
  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const genero = useSignal(props.arguments.genero);
  const album = useSignal(props.arguments.idAlbum);
  const duracion = useSignal(props.arguments.duracion.toString());
  const url = useSignal(props.arguments.url);
  const tipo = useSignal(props.arguments.tipo);
  const ident =useSignal(props.arguments.id);

  const updateCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && 
          url.value.trim().length > 0 &&
          duracion.value.toString().length > 0 &&
          tipo.value &&
          album.value.toString().length > 0 &&
          genero.value.toString().length > 0) {
        const id_genero = parseInt(genero.value) +1;
        const id_album = parseInt(album.value) +1;
        await CancionService.update(parseInt(ident.value),nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionUpdated) {
          props.onCancionUpdated();
        }

        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumGenero().then(data =>
      //console.log(data)
      listaGenero.value = data
    );
  }, []);

  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumCombo().then(data =>
      //console.log(data)
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      //console.log(data)
      listaTipo.value = data
    );
  }, []);

  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Actualizar Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        header={
          <h2
          className='draggable'
          style={{
            flex: 1,
            cursor: 'move',
            margin: 0,
            fronSize: '1.5em',
            fontWeight: 'bold',
            padding: 'var(--lumo-space-m) 0',
          }}
        >
          Editar Cancion
        </h2>
        }
        footerRenderer={() => (
          <>
          <Button onClick={close}>Cancelar</Button>
          <Button theme='primary' onClick={updateCancion}>
            Actualizar
          </Button>
          </>
        )}
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre de la cancion" 
            placeholder="Ingrese el nombre de la cancion"
            aria-label="Nombre de la cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox label="Genero" 
            items={listaGenero.value}
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />
            <ComboBox label="Album"
            items={listaAlbum.value}
            placeholder='Seleccione un album'
            aria-label='Seleccione un album de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
          />
          <ComboBox label="Tipo"
            items={listaTipo.value}
            placeholder='Seleccione un tipo de archivo'
            aria-label='Seleccione un tipo de archivo de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <NumberField label="Duracion"
            placeholder="Ingrese la Duracion de la cancion"
            aria-label="Nombre la Duracion de la cancion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="Link de la cancion"
            placeholder="Ingrese el link de la cancion"
            aria-label="Nombre el link de la cancion"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
            onClick={open}>
            Editar 
          </Button>
    </>
  );
};

//LISTA Canciones
export default function CancionListView() {
  const callData = () => {
    CancionService.listAllCancion().then(function (data) {
    console.log('Datos cargados:', data);
    });
  }

  const [items, setItems] = useState([]);
  useEffect(() => {
    callData();
  }, []);

  const order = (event, columnID) => {
    console.log(event);
    const direction = event.detail.value;

    var dir = (direction === 'asc' ? 1 : -1);
    CancionService.order(columnID, dir).then(function (data) {
      setItems(data);
    });
  }

  const criterio= useSignal('');
  const texto= useSignal('');

  const itemSelect=[
    {
      label: 'Nombre',
      value: 'nombre'
    },
    {
      label: 'Duracion',
      value: 'duracion'
    },
    {
      label: 'Url',
      value: 'url'
    },
    {
      label: 'id_ Genero',
      value: 'id_genero'
    },
    {
      label: 'id_Album',
      value: 'id_album'
    },
    {
      label: 'Tipo',
      value: 'tipo'
    }
  ];

   const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      CancionService.search(criterio.value, texto.value, 0).then(function (data) {
        setItems(data);
      });
      criterio.value = '';
      texto.value = '';
      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  function link({ item }: { item: Cancion }) {
    return (
      <span>
       <CancionEntryFormUpdate arguments={item} onCancionUpdated={callData} />
      </span>
    );
  }

  function index({ model }: { model: GridItemModel<Cancion> }) {
    return ( 
      <span>
        {model.index + 1}
        </span>
    );
  }


  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Canciones">
        <Group>
          <CancionEntryForm onCancionCreated={callData} />
        </Group>
      </ViewToolbar>
      <HorizontalLayout theme="spacing">
        <Select items={itemSelect}
          value={criterio.value}
          onValueChanged={(evt) => (criterio.value = evt.detail.value)}
          placeholder="Selecione un cirterio">


        </Select>

        <TextField
          placeholder="Search"
          style={{ width: '50%' }}
          value={texto.value}
          onValueChanged={(evt) => (texto.value = evt.detail.value)}
        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          BUSCAR
        </Button>
      </HorizontalLayout>
      <Grid items={items}>
      <GridColumn header="Nro" renderer={index} />
      <GridSortColumn path="nombre" header="Nombre" onDirectionChanged={(e) => order(e, 'nombre')} />
      <GridSortColumn path="duracion" header="Duracion" onDirectionChanged={(e) => order(e, 'duracion')} />
      <GridSortColumn path="tipo" header="Tipo" onDirectionChanged={(e) => order(e, 'tipo')} />
      <GridSortColumn path="id_album" header="Album" onDirectionChanged={(e) => order(e, 'id_album')} />
      <GridSortColumn path="id_genero" header="Genero" onDirectionChanged={(e) => order(e, 'id_genero')} />
      <GridSortColumn path="url" header="Url" onDirectionChanged={(e) => order(e, 'url')} />  
      <GridColumn header="Acciones" renderer={link} />
    </Grid> 
     </main >
  );
}
